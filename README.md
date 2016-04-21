Configuration Instructions
--------------------------
 - Get the project from GitHub repository:
git clone https://github.com/dushk/delectable.git
 - Set execute permission for build script to be runnable by the following command:
chmod u+x build_script.sh
 - The build script will then configure the environment to run the project.
NOTE : For the build script to work, the 'delectable' project folder must be placed in the home directory.

Build and deploy instructions
-----------------------------
 - The build script builds and deploys the executable (.war file).
 - Open a browser with a plugin similar to HttpRequester for Mozilla Firefox for making HTTP requests.

Copyright and licensing
------------------------
 - Code and documentation copyright 2016 Dushyanth Kesavan. Code released under the MIT license. Docs released under Creative Commons.

Known bugs
----------
 - If input does not match API, 404 Error is thrown but cannot find out where the error is.
 - If dates provided for revenue report do not match format yyyyMMdd, revenue report for all the orders is given.

Credits and acknowledgements
----------------------------
 - Virgil Bistriceanu, M.S., Adjunct Faculty, Computer Science, Illinois Institute of Technology
