(defproject calendar "0.1.0"
  :license {:name "AGPL-3.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.logging "1.2.4"]
                 [ch.qos.logback/logback-classic "1.4.7"]
                 [mount "0.1.17"]
                 [aleph "0.6.1"]
                 [metosin/reitit "0.6.0"]
                 [metosin/ring-http-response "0.9.3"]
                 [hikari-cp "3.0.1"]
                 [org.postgresql/postgresql "42.6.0"]
                 [com.github.seancorfield/next.jdbc "1.3.874"]
                 [dev.weavejester/ragtime.core "0.9.3"]
                 [dev.weavejester/ragtime.next-jdbc "0.9.3"]
                 [com.github.seancorfield/honeysql "2.4.1026"]
                 [buddy/buddy-auth "3.0.323"]
                 [buddy/buddy-hashers "1.8.158"]
                 [buddy/buddy-sign "3.4.333"]]
  :main ^:skip-aot calendar.core
  :target-path "target/%s"
  :resource-paths ["config" "public"]
  :profiles {:dev {:source-paths ["dev/api"]
                   :dependencies [[org.clojure/tools.namespace "1.4.4"]]
                   :plugins [[lein-ancient "0.7.0"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
