(ns friend-test.views.main
  (:require [friend-test.views.common :as common]
            [hiccup.form :as form]
            [hiccup.element :as element]
            [cemerick.friend :as friend]))

(defn index []
  (common/layout
   "Landing Page"
   [:h1 "Landing Page"]
   (element/link-to "/login" "Login")
   [:br]
   (element/link-to "/logout" "Logout")
   [:br]
   (element/link-to "/debug" "Show authentication")))

(defn login [params]
  (common/layout
   "friend test login form"
   (form/form-to [:post "/login"]
                (form/label "username" "username")
                (form/text-field "username")
                [:br]
                (form/label "password" "password")
                (form/password-field "password")
                [:br]
                (form/submit-button "Submit"))
   (when params
     [:div
      [:ul (for [[k v] params :when (not (= k ""))]
             [:li (str k " " v)])]])))

(defn profile []
  (common/layout
   "User Profile Page"
   [:h1 "User Profile Page"]))

(defn account []
  (common/layout
   "User Account Page"
   [:h1 "User Account Page"]))

(defn dashboard []
  (common/layout
   "Admin Dashboard Page"
   [:h1 "Admin Dashboard Page"]))

(defn debug [request]
  (common/layout
   "Debug Page"
   (list
    [:h2 "current authentication:"]
    (when-let [current-auth (friend/current-authentication)]
      [:ul
       (for [[k v] current-auth]
         [:li (str k " " v)])])
   [:h2 "current session:"]
   [:ul
    (for [[k v] (:session request)]
      [:li (str k " " v)])]
   [:h2 "auth-config:"]
   [:ul
    (for [[k v] (:cemerick.friend/auth-config request)]
      [:li (str k " " v)])]
   (element/link-to "/login" "login")
   [:br]
   (element/link-to "/logout" "logout"))))