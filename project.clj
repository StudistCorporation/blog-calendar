(defproject calendar "0.1.0"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [mount "0.1.16"]
                 [aleph "0.6.1"]
                 [metosin/reitit "0.5.18"]]
  :main ^:skip-aot calendar.core
  :target-path "target/%s"
  :profiles {:dev {:source-paths ["dev/api"]
                   :resource-paths ["public"]
                   :dependencies [[org.clojure/tools.namespace "1.3.0"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
