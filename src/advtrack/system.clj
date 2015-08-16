(ns advtrack.system
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]
            [duct.component.hikaricp :refer [hikaricp]]
            [duct.middleware.not-found :refer [wrap-not-found]]
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [advtrack.endpoint.example :refer [example-endpoint]]))

(def base-options
  {:app {:middleware [[wrap-not-found :not-found]
                      [wrap-webjars]
                      [wrap-defaults :defaults]]
         :not-found  (io/resource "errors/404.html")
         :defaults   site-defaults}})

(defn new-system [options]
  (let [options (meta-merge base-options options)]
    (-> (component/system-map
         :app  (handler-component (:app options))
         :http (jetty-server (:http options))
         :db   (hikaricp (:db options))
         :example (endpoint-component example-endpoint))
        (component/system-using
         {:http [:app]
          :app  [:example]
          :example [:db]}))))
