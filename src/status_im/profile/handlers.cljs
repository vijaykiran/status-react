(ns status-im.profile.handlers
  (:require [re-frame.core :refer [subscribe dispatch]]
            [status-im.utils.handlers :refer [register-handler]]
            [status-im.components.react :refer [show-image-picker
                                                copy-to-clipboard]]
            [status-im.components.share :refer [open]]
            [status-im.utils.image-processing :refer [img->base64]]
            [status-im.i18n :refer [label]]
            [status-im.utils.handlers :as u :refer [get-hashtags]]
            [clojure.string :as str]
            [status-im.profile.validations :as v]
            [cljs.spec :as s]
            [taoensso.timbre :as log]))

(defn message-user [identity]
  (when identity
    (dispatch [:navigate-to :chat identity])))

(register-handler :open-image-picker
   (u/side-effect!
     (fn [_ _]
       (show-image-picker
         (fn [image]
           (let [path       (get (js->clj image) "path")
                 _ (log/debug path)
                 on-success (fn [base64]
                              (dispatch [:set-in [:profile-edit :photo-path] (str "data:image/jpeg;base64," base64)]))
                 on-error   (fn [type error]
                              (.log js/console type error))]
             (img->base64 path on-success on-error)))))))

(register-handler :open-image-source-selector
  (u/side-effect!
    (fn [_ [_ list-selection-fn]]
      (list-selection-fn {:title       (label :t/image-source-title)
                          :options     [(label :t/image-source-make-photo) (label :t/image-source-gallery)]
                          :callback    (fn [index]
                                         (case index
                                           0 (dispatch [:navigate-to :profile-photo-capture])
                                           1 (dispatch [:open-image-picker])
                                           :default))
                          :cancel-text (label :t/image-source-cancel)}))))

(register-handler :open-sharing
  (u/side-effect!
    (fn [_ [_ list-selection-fn text dialog-title]]
      (list-selection-fn {:title       dialog-title
                          :options     [(label :t/sharing-copy-to-clipboard) (label :t/sharing-share)]
                          :callback    (fn [index]
                                         (case index
                                           0 (copy-to-clipboard text)
                                           1 (open {:message text})
                                           :default))
                          :cancel-text (label :t/sharing-cancel)}))))
