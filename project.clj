(defproject isolated-integration-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [environ "1.0.1"]
                 [ragtime "0.5.2"]
                 [org.clojure/tools.nrepl "0.2.10"]
                 [org.postgresql/postgresql "9.4.1207.jre7"]
                 [prismatic/schema "1.1.3"]
                 [prismatic/schema-generators "0.1.0"]
                 [yesql "0.5.2"]]
  :plugins       [[lein-environ "0.4.0"]]
  :eval-in :nrepl
  :profiles {:dev [:project/dev :profiles/dev]
             :project/dev {:dependencies [[midje "1.6.3"]]
                           :plugins [[lein-midje "3.1.3"]]
                           ;;when :nrepl-port is set the application starts the nREPL server on load
                           :env {:dev true
                                 :port 3000
                                 :nrepl-port 7000}}
             :project/test {}}
   :aliases {"migrate" ["run" "-m" "isolated-integration-test.conf.migrations/migrate"]
             "rollback" ["run" "-m" "isolated-integration-test.conf.migrations/rollback"]}
   :min-lein-version "2.5.3"
   :resource-paths ["src" "resources"])
