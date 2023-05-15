(ns calendar.effects
  (:require [re-frame.core :as rf]
            [cognitect.transit :as t]))

(rf/reg-fx
 ::dialog
 (fn [[mode e]]
   (let [dialog (js/document.getElementById "dialog")
         form (js/document.getElementById "form")]
     (case mode
       :show
       (.showModal dialog)

       :close
       (do
         (.close dialog)
         (.reset form))

       :reset
       (some-> e .-target .reset)

       ;; else
       (when-let [target (.-target e)]
         (if (= "DIALOG" (.-nodeName target))
           (do
             (.close dialog)
             (.reset form))
           (.stopPropagation e)))))))

(def local-storage-key
  "personal-calendar-2022-TODO")

(def persist
  (rf/after
   (fn [{data :days}]
     (let [encoder (t/writer :json)]
       (->> data
            (t/write encoder)
            (js/localStorage.setItem local-storage-key))))))

(rf/reg-cofx
 ::persisted
 (fn [state _]
   (let [decoder (t/reader :json)
         data (some->> local-storage-key
                       (js/localStorage.getItem)
                       (t/read decoder))]
     (assoc state :persisted data))))
