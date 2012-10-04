(defproject friend-test "0.1.0-SNAPSHOT"
  :description "take authentication using friend out for a spin"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.cemerick/friend "0.1.2" :exclusions [ring/ring-core]]
                 [compojure "1.1.3"]
                 [clj-http "0.5.5"]
                 [hiccup "1.0.1"]]
  :plugins [[lein-ring "0.7.5"]]
  :ring {:handler friend-test.core/secured-app})
