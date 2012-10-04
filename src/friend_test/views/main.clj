(ns friend-test.views.main
  (:require [friend-test.views.common :as common]
            [hiccup.form :as form]))

(defn index []
  (common/layout
   "Landing Page"
   [:h1 "Landing Page"]))

(defn login []
  (common/layout
   "friend test login form"
   (form/form-to [:post "/login"]
                (form/label "username" "username")
                (form/text-field "username")
                [:br]
                (form/label "password" "password")
                (form/password-field "password")
                [:br]
                (form/submit-button "Submit"))))
