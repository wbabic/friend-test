(use 'cemerick.friend.workflows :reload)
(use 'cemerick.friend.credentials)
(use 'ring.mock.request)

(def users {"root"
            {:username "root"
             :password (hash-bcrypt "admin_password")
             :roles #{::admin}}
            "walter"
            {:username "walter"
             :password (hash-bcrypt "user_password")
             :roles #{::user}}})

(interactive-login-redirect )

(def my-form-handler (interactive-form
                      :login-uri "/my_login"
                      :credential-fn
                      (fn [{:keys [username password] :as creds}]
                        {:identity "me"})))

(def my-form-handler (interactive-form
                      :login-uri "/my_login"
                      :credential-fn
                      (partial bcrypt-credential-fn users)))

(request :get "my_login")

(my-form-handler (request :get "/my_login"))

(my-form-handler (request :post "/my_login"))
(meta (my-form-handler (request :post "/my_login")))

(my-form-handler (assoc (request :post "/my_login")
                   :params {:username "walter" :password "user_password"}))

(def resp (my-form-handler (assoc (request :post "/my_login")
                   :params {:username "walter" :password "user_password"})))
(meta resp)

(def in
  [{:name "jay fields", :current-city "new york", :employer "drw.com"}
   {:name "john dydo", :current-city "new york", :employer "drw.com"}
   {:name "mike ward", :current-city "chicago", :employer "drw.com"}
   {:name "chris george", :current-city "new york", :employer "thoughtworks.com"}])

;; desired output
;; {"thoughtworks.com" ("chris george"), "drw.com" ("jay fields" "john dydo" "mike ward")}

(->> in
     (map (juxt :employer (comp list :name)))
     (map (partial apply hash-map))
     (apply merge-with concat))

(filter (comp #{"new york"} :current-city) in)
(require 'clojure.set)

(clojure.set/join [{:current-city "new york"}] in)
(clojure.set/join [{:employer "thoughtworks.com"}] in)
(clojure.set/join [{:employer "drw.com"}] in)

;; alternative approach
(defn mod-vals [f m]
  (reduce-kv (fn [r k v]
               (assoc r k (map f v)))
             {}
             m))
(group-by :employer in)
(mod-vals :name (group-by :employer in))

(defn update-values [m f & args]
  (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))
(-> (group-by :employer in)
;;    (update-values :name)
    )
;; not working


;; params map
(for [[k v] {:k1 "val1" :k2 "val2"}]
  [:li (str "key= " k " val= " v)])