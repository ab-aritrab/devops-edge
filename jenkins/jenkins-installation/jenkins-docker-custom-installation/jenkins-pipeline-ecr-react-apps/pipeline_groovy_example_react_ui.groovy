pipeline {
    agent none
    environment {
        NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
        registry = "995133248974.dkr.ecr.us-east-1.amazonaws.com/dev-arpian-repository"
    }
    stages {
        stage('checkout'){
            agent any
            steps{
                script{
                    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'bitbucket-cred', url: 'https://ab-aritrab@bitbucket.org/arpiantech_animesh/ag-grid-react-example.git']]])
                }
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'node:16.15.1'
                    args '-p 3000:3000'
                    //args '-u root:root'
                }
            }
            // environment {
            //     NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
            // }
            steps {
                sh 'npm install --legacy-peer-deps --unsafe-perm=true'
                sh 'npm run build'
            }
        }
        stage('BuildImage') {
            agent any
            steps {
                sh 'pwd'
                sh 'cp -rp ${WORKSPACE}/dist/examples ${WORKSPACE}/jenkins-job/'
                sh 'cd ${WORKSPACE}/jenkins-job'
                sh 'docker build -t 995133248974.dkr.ecr.us-east-1.amazonaws.com/dev-arpian-repository -f ${WORKSPACE}/jenkins-job/Dockerfile .'
            }
        }

        // Uploading Docker images into AWS ECR
        stage('Pushing to ECR') {
            agent any
            steps{  
                script {
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 995133248974.dkr.ecr.us-east-1.amazonaws.com'
                    sh 'docker push 995133248974.dkr.ecr.us-east-1.amazonaws.com/dev-arpian-repository:latest'
                    // This step should not normally be used in your script. Consult the inline help for details.
                    // withDockerRegistry(credentialsId: 'ecr:us-east-1:aws_arpiantech_ecs', url: '995133248974.dkr.ecr.us-east-1.amazonaws.com/dev-arpian-repository') {
                    //     docker.image("registry").push()
                    // }
                    // docker.withRegistry("https://995133248974.dkr.ecr.us-east-1.amazonaws.com/dev-arpian-repository", "ecr:us-east-1:aws_arpiantech_ecs") {
                    //     //dockerImage.push()
                    //     docker.image("example-webapp").push()
                    // }
                }
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