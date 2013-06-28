(defproject friend-test "0.1.1"
  :description "take authentication using friend out for a spin"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.cemerick/friend "0.1.5"]
                 [cheshire "5.2.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]
                 [ring-mock "0.1.5"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler friend-test.core/secured-app})
