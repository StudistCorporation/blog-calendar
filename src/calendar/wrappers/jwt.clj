(ns calendar.wrappers.jwt
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.tools.logging :as log]
            [mount.core :refer [defstate]]
            [buddy.auth.backends :refer [jws]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.core.keys :as keys]
            [buddy.sign.jwt :as jwt]))

(defstate secret
  :start
  (->> (io/file "config/jwt.key") (slurp) (str/trim)))

(def algo
  :hs256)

(defn decode
  [message]
  (when message
    (try
      (jwt/unsign
       message
       secret
       {:alg algo})
      (catch Throwable ex
        (log/info "JWT error:" (.getMessage ex))
        nil))))

(defstate backend
  :start
  (jws {:token-name "Bearer"
        :secret secret
        :authfn identity
        :on-error (fn [_ ^Throwable err] (log/debug err (.getMessage err)))
        :options {:alg algo}}))

(defn wrap-jwt
  [handler]
  (fn jwt-wrapper
    [request]
    ((wrap-authentication handler backend) request)))
