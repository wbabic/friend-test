(ns friend-test.core
  (:require [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            (compojure [handler :as handler]
                       [route :as route])
            [cheshire.core :as json]
            [ring.util.response :as resp]
            [friend-test.views.main :as main])
  (:use [compojure.core :as compojure :only (GET ANY defroutes)]))

(defn- json-response
  [x]
  (-> (json/generate-string x)
      resp/response
      (resp/content-type "application/json")))

(def users {"root"
            {:username "root"
             :password (creds/hash-bcrypt "admin_password")
             :roles #{::admin}}
            "walter"
            {:username "walter"
             :password (creds/hash-bcrypt "user_password")
             :roles #{::user}}})

(derive ::admin ::user)

(defroutes user-routes
  (GET "/account" request (main/account))
  (GET "/profile" request (main/profile)))

(defroutes admin-routes
  (GET "/dashboard" request "Admin Dashboard Page"))
 
(defroutes my-ring-app
  (compojure/context "/user" request (friend/wrap-authorize user-routes #{::user}))
  (compojure/context "/admin" request (friend/wrap-authorize admin-routes #{::admin}))
  (GET "/" [] (main/index))
  (GET "/hello" [] "<h1>Hello World</h1>")
  (GET "/login" request
       (main/login (:params request)))
  (friend/logout (ANY "/logout" request (ring.util.response/redirect "/")))
  (GET "/echo-roles" request (friend/authenticated
                              (-> (friend/current-authentication)
                                  (select-keys [:roles])
                                  json-response)))
  (GET "/debug" request (main/debug request)))

(def secured-app
  (-> my-ring-app
      (friend/authenticate {:credential-fn (partial creds/bcrypt-credential-fn users)
                            :workflows [(workflows/interactive-form)]})
      (handler/site)))
