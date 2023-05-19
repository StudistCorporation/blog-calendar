(ns calendar.effects.http
  (:require [re-frame.core :as rf]))

(rf/reg-fx
 ::http
 (fn [{:keys [path on-success on-error opts]
       :or {opts {}}}]
   (-> (js/fetch
        (str path)
        (clj->js (merge {:redirect :error
                         :signal (js/AbortSignal.timeout 500)
                         :cache :reload
                         :credentials :omit}
                        opts)))
       (.then (fn [response]
                (if (<= 200 (.-status response) 299)
                  (-> (.json response)
                      (.then #(rf/dispatch
                               (conj on-success
                                     (js->clj % :keywordize-keys true))))
                      (.catch #(rf/dispatch (conj on-error %))))
                  (rf/dispatch (conj on-error response)))))
       (.catch #(rf/dispatch (conj on-error %))))))
