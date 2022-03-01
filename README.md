# IIUMCourseScheduleApp
This is an Android App that scrapes data from the IIUM Course Schedule website and display in neatly fashion.
It requires Internet Connection each time the scrapper is activated. 
### Original Idea from: https://github.com/asdacap/iiumschedule. 
I fixed the scraper so now it's more robust in collecting the data.
### Also check out this Flutter implementation! 
#### https://github.com/iqfareez/iium_schedule

## What It Does
1. A functioning scraper. The scraper can be found in the source files. It's fully functional, and can be ported to other languages such as Dart or C#.
2. Display it in a RecyclerView
3. Nav Bar, but doesn't do much
4. Each course now has a page that displays its full info, albeit using Android WebView. Will try to implement more efficient solutions.

## What It Doesn't
1. Search Function
2. Fully working Nav Bar
3. Back Button that goes back to Main Activity
4. Database in case for offline use (maybe just save it into a JSON file?)

## What It Should Have
1. A good functioning UI and cohesive UX.

## Future Plans
1. Refactor the code into Kotlin for easier porting to Desktop via Kotlin MultiPlatform
2. Reimplement course details page using more efficient methods. (Webview is disgustingly eating memory for just a simple application)
3. Schedule Maker
