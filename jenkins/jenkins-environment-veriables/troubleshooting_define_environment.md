JENKINS ERROR (from console output)- 

+ CI=false npm run build:test

> ezbanc_console@1.0.0 build:test /var/lib/jenkins/workspace/ezbanc-uat/frontend-test2
> env-cmd -f .test.env react-scripts build

/var/lib/jenkins/workspace/ezbanc-uat/frontend-test2/node_modules/dotenv-expand/lib/main.js:11
      var parts = /(.?)\${?([a-zA-Z0-9_]+)?}?/g.exec(match)
                                                ^

==================
Solution -
https://stackoverflow.com/questions/49287598/maximum-call-stack-size-exceeded-from-dotenv-expand-on-circleci-using-react

TAG: 'Maximum call stack size exceeded' from dotenv-expand on CircleCI using React
------------------------
18
--
Turns out I'd been importing environmental variables using the same name e.g. REACT_APP_API_KEY_GOOGLE_MAPS=${REACT_APP_API_KEY_GOOGLE_MAPS} . Once I changed the name e.g. REACT_APP_API_KEY_GOOGLE_MAPS=${REACT_APP_API_KEY_GOOGLE_MAPS_EXT} this issue was resolved!

------------------------
===================