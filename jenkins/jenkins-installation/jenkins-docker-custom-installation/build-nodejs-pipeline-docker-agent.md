### NODE APP BUILD:

##### Pipeline scripts -

****** ERROR CODE: *********
```

[Pipeline] sh
+ npm install --unsafe-perm=true --allow-root
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
Unhandled rejection Error: EACCES: permission denied, mkdir '/.npm'
npm ERR! cb() never called!

npm ERR! This is an error with npm itself. Please report this error at:
npm ERR!     <https://npm.community>

```
****** SOLUTION: *********
```
Replace CI = 'true' to  NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"

ERROR SYNTEX -
environment {
    CI = 'true'
}

SOLUTION SYNTEX -

environment {
    NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
}


```
###### MAIN CODE LOOK LIKE:
```
=======================
pipeline {
    agent {
        docker {
            image 'node:lts-buster-slim'
            args '-p 3000:3000'
        }
    }
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
    }
    stages {
        stage('checkout'){
            steps{
                script{
                    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/jenkins-docs/simple-node-js-react-npm-app.git']]])
                }
            }
        }
        stage('Build') {
            steps {
                sh 'npm install --unsafe-perm=true'
                sh 'npm run build'
            }
        }
        // stage('Test') {
        //     steps {
        //         sh './jenkins/scripts/test.sh'
        //     }
        // }
        // stage('Deliver') {
        //     steps {
        //         sh './jenkins/scripts/deliver.sh'
        //         input message: 'Finished using the web site? (Click "Proceed" to continue)'
        //         sh './jenkins/scripts/kill.sh'
        //     }
        // }
    }
}

```

###### LINKS: 
https://stackoverflow.com/questions/71079901/eacces-permission-denied-when-running-npm-install-by-jenkins-and-using-docker
https://stackoverflow.com/questions/62330354/jenkins-pipeline-alpine-agent-apk-update-error-unable-to-lock-database-permis/62330483#62330483

###### DEMO LINKS:
https://github.com/jenkins-docs/simple-node-js-react-npm-app


git clone https://ab-aritrab@bitbucket.org/arpiantech_animesh/ag-grid-react-trader.git