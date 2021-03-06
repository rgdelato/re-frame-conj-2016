(ns show.steps.step15
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [re-frisk.core :as re-frisk]
    [cljs.spec :as s]
    [show.slides.views :as views]))

(declare slides)

(def initial-state
  {:slideshow/current-slide 0})

(re-frame/reg-event-fx
  :slideshow/initialize
  (fn [{:keys [db]}]
    {:db initial-state}))

#_(re-frame/dispatch
    [:slideshow/initialize])

(defn check-int-spec [val]
  (s/valid? (s/spec int?) val))

(re-frame/reg-event-fx
  :slideshow/next-slide
  (fn [{:keys [db event] :as context}]
    (re-frisk/add-data (first event)
                       {:context context
                        :db      db
                        :event   event})
    (if
      (check-int-spec
        (:slideshow/current-slide db))
      {:db
       (assoc db
         :slideshow/current-slide
         (if (< (:slideshow/current-slide db)
                (count slides))
           (+ 1 (:slideshow/current-slide db))
           (:slideshow/current-slide db)))}
      {:db
       (assoc db
         :spec/error {:event   (first event)
                      :message ":slideshow/current-slide is not a valid int"})})))

(re-frame/reg-event-fx
  :slideshow/prev-slide
  (fn [{:keys [db event] :as context}]
    (re-frisk/add-data
      (first event)
      {:context context
       :db      db
       :event   event})
    (if
      (check-int-spec
        (:slideshow/current-slide db))
      {:db
       (assoc db
         :slideshow/current-slide
         (if (> (:slideshow/current-slide db) 0)
           (- (:slideshow/current-slide db) 1)
           (:slideshow/current-slide db)))}
      {:db
       (assoc-in db
                 :spec/error {:event   (first event)
                              :message ":slideshow/current-slide is not a valid int"})})))

#_(re-frame/dispatch
    [:slideshow/prev-slide])

#_(re-frame/dispatch
    [:slideshow/next-slide])

(defn slideshow []
  [:div.slideshow-half-width
   [:div.slide
    [(nth slides 0)]]])

(defn slide-footer
  [footer-text]
  [:div.slide-footer
   [:span ""]
   [:span footer-text]
   [:span.controls
    [:i.material-icons.control.left
     {:on-click
      #(re-frame/dispatch
        [:slideshow/prev-slide])}
     "chevron_left"]
    [:i.material-icons.control.right
     {:on-click
      #(re-frame/dispatch
        [:slideshow/next-slide])}
     "chevron_right"]]])

(defn title-slide []
  [:div.title-slide
   [:div.slide-body
    [:br]
    [:div "Building a "]
    [:div "Presentation with"]
    [:img.re-frame-logo
     {:src   "img/re-frame-logo.png"
      :style {:margin-top "20px"}}]
    [:div "and"]
    [:img.cljs-logo
     {:src "img/cljs-logo.png"}]]
   [slide-footer "Clojure/conj 2016"]])

(defn intro-slide []
  [:div.intro-slide
   [:div.slide-header "marketing slide"]
   [:div.slide-body
    [:div.list "- Great for beginners and quick prototypes!"]
    [:div.list "- Great for large, complex apps!"]
    [:div.list "- Performant at scale!"]
    [:div.list "- Multiple production apps of 50,000 lines and more!"]]
   [slide-footer ""]])

(defn mount-root []
  (reagent/render
    [slideshow]
    (js/document.getElementById "app")))

(defn on-jsload []
  (mount-root))

(defn ^:export run []
  (re-frame/dispatch-sync
    [:slideshow/initialize])
  (re-frisk/enable-re-frisk!
    {:x 0 :y 0})
  (mount-root))


(defn intro-slide-1 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   [:div.slide-body
    [:div.list "- Mutation is fundamental to SPAs"
     [:div.list "- Change the DOM"]
     [:div.list "- Change our application state"]
     [:div.list "- Change the World"
      [:div.list "- Local Storage"]
      [:div.list "- Cookies"]
      [:div.list "- APIs"]
      [:div.list "- Databases"]]]]
   [slide-footer ""]])

(defn intro-slide-2 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   [:div.slide-body
    [:div.list "- But we want to program functionally"
     [:div.list "- To change the DOM"]
     [:div.list "- To change our application state"]
     [:div.list "- To change the World!"]
     [:br] [:br]
     [:div.list "re-frame can help us do this"]]]
   [slide-footer ""]])

(defn domino-1 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   views/domino-1
   [slide-footer ""]])

(defn domino-2 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   views/domino-2
   [slide-footer ""]])

(defn domino-3 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   views/domino-3
   [slide-footer ""]])

(defn domino-4 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   views/domino-4
   [slide-footer ""]])

(defn domino-5 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   views/domino-5
   [slide-footer ""]])

(defn domino-6 []
  [:div.intro-slide
   [:div.slide-header "re-frame intro"]
   views/domino-6
   [slide-footer ""]])


(defn our-job []
  [:div.intro-slide
   [:div.slide-header "Getting Started"]
   [:div.slide-body
    [:div.list "- Define Views"]
    [:div.list "- Define the Initial Shape of App State"]
    [:div.list "- Register Events"]
    [:div.list "- Register Subscription Queries"]
    [:div.list "- Link Queries to Views"]
    [:div.list "- Link Events to Views"]]
   [slide-footer ""]])


(defn other-things []
  [:div.intro-slide
   [:div.slide-header "Growing Your App"]
   [:div.slide-body
    [:div.list "- Build Context with Coeffects"
     [:div.list [:a {:href "http://tomasp.net/coeffects/"}
                 "http://tomasp.net/coeffects/"]]]
    [:div.list "- Define your own effects"]
    [:div.list "- Interceptors (inspired by Pedestal)"]]
   [slide-footer ""]])

(defn ending-slide []
  [:div.intro-slide
   [:div.slide-header "Thank You!"]
   [:div.slide-body

    [:div.list [:a {:href "https://github.com/Day8/re-frame"}
                "https://github.com/Day8/re-frame"]]
    [:div.list [:a {:href "http://re-frame.org"}
                "http://re-frame.org"]]
    [:div.list "#re-frame on Clojurians Slack"]
    [:div.list "#re-frame on Conj Slack"]
    [:br] [:br] [:br] [:br]
    [:div.list "@shaun-mahood on Clojurians Slack"]
    ]
   [slide-footer ""]])

(def slides
  [title-slide
   intro-slide
   intro-slide-1
   intro-slide-2
   domino-1
   domino-2
   domino-3
   domino-4
   domino-5
   domino-6
   our-job
   other-things
   ending-slide])