(ns friend-test.views.common
  (:require [hiccup.page :as page]))

(defn layout [title & content]
  (page/html5
   [:head
    [:title title]
    [:body
     [:div#content content]]]))