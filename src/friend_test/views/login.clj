(ns friend-test.views.login
  (:require [hiccup.page :as p]
            [hiccup.form :as f]))

(defn login []
  (p/html5
   [:head
    [:title "friend test login form"]]
   [:body
    [:div {:id "login-form"}
     (f/form-to [:post "/login"]
                (f/label "username" "username")
                (f/text-field "username")
                [:br]
                (f/label "password" "password")
                (f/password-field "password")
                [:br]
                (f/submit-button "Submit"))]]))