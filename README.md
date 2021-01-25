# Hobbits from Reddit

This is an native Android app that uses Reddit API https://www.reddit.com/dev/api to up-vote or down-vote a post selected by the app from the first 100 posts of the subreddit https://www.reddit.com/r/lotr/. It is going to be defined how to test this app, how a post is selected and some characterics of the app.

## How to test it?

For this app porpouse, there is no need to build an apk, but to test its functionalities it is required to run it on Android Studio emulator or in a device so you can see the logcat where some result are printed. It is able to run from API 23 to last android version. Once it is running you will see on screen a post title and its image and next to them there are a couple of buttons that you can press in order to sign in to Reddit and after, vote the post.

## Post selected 

First, these 100 posts are separeted in groups defined by the name of next Hobbits:

 - "Frodo"
 - "Bilbo"
 - "Samsagaz" o "Sam"
 - "Meriadoc" o "Merry"
 - "Peregrin" o "Pippin"
 - "Smeagol" o "Gollum"
 
If these names form part of the title of the post, that post is going to be inside a collection of that Hobbit. There are Hobbits that have two names so if a title has any of these names, it will be in the same collection. 

Next step is to order the Hobbit collections by descending order in function of the number of posts that have each one. Now there is a list of collections that can be printed in console in JSON format and it can be seen with logcat window on Android studio once the app is running. 

![alt text](https://github.com/robertovega20/Projects-Images/blob/master/Screen%20Shot%202021-01-24%20at%2018.01.20.png)


The biggest collection is proccesed to take one of the post of that Hobbit. This post has to have an image and also has to have the biggest title length of that collection. If there is no image, the post selected also is the one with the biggest title.

This post selected is passed to the view where can be seen its title and its image. 
As a feature you can up-vote and down-vote tha post if you want, for this to be possible you have to sign in into your Reddit account with a dialog that is shown after you try to press a button. 
![alt text](https://github.com/robertovega20/Projects-Images/blob/master/Screenshot_1611530437.png)
![alt text](https://github.com/robertovega20/Projects-Images/blob/master/Screenshot_1611530446.png)

After sign in, it is required that the user allow this app some permisses on Reddit.

![alt text](https://github.com/robertovega20/Projects-Images/blob/master/Screenshot_1611530481.png)

Then the user can vote the post. That's it.

![alt text](https://github.com/robertovega20/Projects-Images/blob/master/Screenshot_1611530492.png)

## Some App characteristics

This app follows MVVM pattern. This pattern could not be the most appropiete for a little app like this. But it helps understand how to build an app that can be scaled and well tested. 

It uses... 
  - Coroutines to handle network calls.
  - Dagger Hilt for DI. 
  - Shared preferences in order to have some persistance data.
  - Flipper interceptor for network debbuging. 
  - Retrofit2 as a safe-type HTTP client.
  - Moshi to parse data from a network response.
  - Tikxml converter to parse xml information from the subreddit.
  - Glide for images that have to be taken from a url.
  

Thanks for read.
