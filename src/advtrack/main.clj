(ns advtrack.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.tools.reader.edn :as edn]
            [com.stuartsierra.component :as component]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [meta-merge.core :refer [meta-merge]]
            [advtrack.config :as config]
            [advtrack.system :refer [new-system]]))

(def prod-options
  {:app {:middleware     [[wrap-hide-errors :internal-error]]
         :internal-error (io/resource "errors/500.html")}})

(def options
  (if (.exists ^File (io/as-file "advtrack.edn"))
    (meta-merge prod-options (edn/read-string (slurp "cmw.edn")))
    prod-options))

(defn -main [& args]
  (let [system (new-system options)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (component/start system)))
