(defproject friend-test "0.1.1"
  :description "take authentication using friend out for a spin"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.cemerick/friend "0.1.4" :exclusions [ring/ring-core]]
                 [compojure "1.1.3"]
                 [clj-http "0.5.5"]
                 [hiccup "1.0.1"]
                 [ring-mock "0.1.3"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler friend-test.core/secured-app})
