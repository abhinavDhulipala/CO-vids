# CO-vids
Android app to help more effeciently deploy scarse resources.

## Inspiration

COVID-19 testing supply shortages have ignited concerns in people about whether they or their loved ones have caught the disease. Many areas have resorted to testing only those who show symptoms, limiting the potential effectiveness of the tests and doing little to calm a nervous public. We noticed the critical bottleneck is that tests are usually applied retroactively and only to those who are already likely ill. This bottleneck will become increasingly constraining as governments worldwide ease their social distancing restrictions, allowing the disease to quickly spread across towns, forcing them to reapply blanket quarantines.

## The Solution

	COVids kill two birds with one stone. Through our app, users can fill out a questionnaire and request a 5-minute appointment with a medical professional or volunteer. The app will schedule a video conference where the medical professional will examine the patient and let them know whether they need further testing. This appointment gives users a chance to pre-emptively restrict their own activities and proactively reduce the spread of the disease. It also comforts those who exhibit COVID-like symptoms but lack access to a full-fledged test. Our questionnaire will also immediately recommend that users call emergency services if they report potentially life-threatening symptoms.

	Meanwhile, on the backend, COVids flags positive results and aggregates them by GPS location. It clusters the results with [DBSCAN](https://en.wikipedia.org/wiki/DBSCAN), showing areas of dense positive results. Governments can then test just a few people in these clusters to confirm if a new coronavirus outbreak has begun in the area, magnifying the effectiveness of limited testing supplies. Based on the algorithm’s hyperparameters, the number of flagged submissions, and the disease’s behavior, the government can also calculate the probable radius of spread, allowing them to apply more **granular shutdowns** to areas. **In short, governments can test areas, rather than individuals, for COVID. They can also make probabilistically sound assumptions that certain areas are affected by COVID without administering a single test, given that enough positive results how a strong enough correlation.** DBSCAN detects clusters at the scale of a small neighborhood, allowing even local governments to effectively manage healthcare resources without shutting down their entire town or county.

## How We Built It

	On the front end, our Android application is written in basic XML. We used Firebase to store user locations, questionnaire results, and medical professional availability. The app is fully functional and scalable when it comes to user authentication and GPS data handling. When users sign up for the application, they may complete the questionnaire based on the CDC’s list of symptoms. If the self-checker indicates that the user exhibits symptoms, GPS data is anonymously taken and users are emailed their allotted time-slot with a medical professional. On the other hand, when medical professionals sign up, they are asked for their availability, and these timings are stored on Firebase, all of this will be handled through a scheduler.
	The scheduling system is managed by a Flask server, written in Python, that updates Firebase and sends medical availability to users upon request. When users confirm a timeslot or new professionals submit their availability, it updates Firebase accordingly.
	Additionally, a Python script runs DBSCAN with the Haversine distance between GPS coordinates stored on Firebase at the time of submission. With Numpy, Sklearn, and Matplotlib, the script clusters and visualizes the data enabling public policy leaders and medical professionals to respond rapidly to potential outbreaks. Unlike its popular counterpart, K-Means, DBSCAN can recognize clusters that aren’t necessarily linked to a single centroid (i.e. irregularly shaped clusters), making it an excellent candidate to track COVID.
	To test the DBSCAN algorithm, we simulated sample outbreaks with Gaussian distributions in random counties in Colorado, and then we added random false positives across the state to the dataset. DBSCAN was extremely successful at picking out clusters and ignoring the false positives.

## Challenges We Ran Into

Two major challenges we ran into include integration and testing.

With so many moving parts, it was difficult to integrate all the pieces. For instance, developing the Firebase server to both interfaces between the patients and the professionals as well as between the patients and the DBSCAN algorithm proved difficult. We also had trouble integrating both the users and the professionals in the same application. We were concerned about issues like verifying the professionals’ credentials. Our long-term plan is to make two applications with separate verification processes, but due to time constraints, we were forced to keep just one application.

The second challenge was testing. We couldn’t find past datasets that linked disease spread to GPS locations, which meant we had to mock our own data to test our algorithm. We randomly generated points of origin and randomly sampled points around these origins to simulate an outbreak. We also added false positives to the dataset. This mock data served as test data for our algorithm, giving us a proof-of-concept.

## Accomplishments That We’re Proud Of

We’re proud of using our programming skills to actually try to help people affected by the disease. In times like these, it feels great to pitch in and do what we can to help!

We’re also proud of the amount of thought we put into seemingly small design decisions such as how to approach dividing the application between users and professionals or which clustering algorithm to use. We spent a lot of time weighing the pros and cons behind these ideas before moving forward, and when time is limited these decisions can be crucial.

## What We Learned

We learned how to integrate Firebase with our mobile applications. It’s a great tool that saves time when prototyping database applications. It was especially eye-opening to learn how we can integrate it with our mobile projects, as well as parts of the application that weren’t on mobile.

This is also the first time we’ve ever had to generate our own test data to test the code that we’ve written. It was a challenging and exciting experience, and it opened our eyes to some of the difficulties that come with it. For instance, we struggled with hyperparameter tuning because it was intimately linked with the parameters we used to generate the test data.

## What's next for COVids

We’d like to delve more into the math behind our idea. We’d love to understand how rigorous our approach of testing areas rather than individuals actually are. If it is as robust as we suspect, we plan to better integrate the clustering algorithm with our application, fully integrate a scheduler with a web client for medical professionals, and refine the UI, so people can start using it!
