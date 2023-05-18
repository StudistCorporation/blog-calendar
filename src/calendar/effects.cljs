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
