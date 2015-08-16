(defproject advtrack "0.1.0-SNAPSHOT"
  :description "Application to take input from Osmand Maps and save submitter's position for later map display"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.stuartsierra/component "0.2.3"]
                 [compojure "1.4.0"]
                 [duct "0.1.2"]
                 [environ "1.0.0"]
                 [meta-merge "0.1.1"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring-jetty-component "0.2.2"]
                 [ring-webjars "0.1.0"]
                 [org.webjars/normalize.css "3.0.2"]
                 [duct/hikaricp-component "0.1.0"]
                 [org.postgresql/postgresql "9.4-1201-jdbc4"]]
  :plugins [[lein-environ "1.0.0"]
            [lein-gen "0.2.2"]]
  :generators [[duct/generators "0.1.2"]]
  :duct {:ns-prefix advtrack}
  :main ^:skip-aot advtrack.main
  :aliases {"gen"   ["generate"]
            "setup" ["do" ["generate" "locals"]]}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]
   :uberjar {:aot :all}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:source-paths ["dev"]
                   :repl-options {:init-ns user}
                   :dependencies [[reloaded.repl "0.1.0"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [kerodon "0.6.1"]]
                   :env {:port 3000}}
   :project/test  {}})
