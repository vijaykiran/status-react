(ns status-im.components.image-button.styles)

(def image-button
  {:position   :absolute
   :bottom     2
   :right      16
   :flex       1
   :height     50
   :alignItems :center})

(def image-button-content
  {:flex          1
   :flexDirection :row
   :height        50
   :alignItems    :center
   :alignSelf     :center})

(def image-button-text
  {:flex          1
   :flexDirection :column
   :letter-spacing -0.3
   :margin-left   8})

(def scan-button-text
  (merge image-button-text {:color "#7099e6"}))

(def show-qr-button-text
  (merge image-button-text {:color "#838c93"}))