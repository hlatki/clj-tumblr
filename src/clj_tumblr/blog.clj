(ns clj-tumblr.blog
    (:require [clj-tumblr.core :as tumblr]))

;; info -- API Key
;; http://api.tumblr.com/v2/blog/scipsy.tumblr.com/info
(defn info
  "Get a blog's info (requires API key"
  [blog-name auth]
  (tumblr/api-call {:base "blog" :ident blog-name :call "info" :auth auth}))


;; avatar -- no auth

;; followers - OAUTH



(defn likes
  "Get a blog's likes.  Takes blog name (e.g. blogname.tumblr.com) and API key. Optionally you can have set limit and offset.  Ex. (likes blog-name api-key {:limit 5 :offset 2}) "
  [blog-name auth & [opts]]
  (tumblr/api-call {:base "blog" :ident blog-name :call "likes" :auth auth} opts))



;; Posts -- API key