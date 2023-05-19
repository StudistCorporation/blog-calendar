(ns calendar.interop)

(defn form->map
  [form]
  (-> form
      (js/FormData.)
      (js/Object.fromEntries)
      (js->clj :keywordize-keys true)))
