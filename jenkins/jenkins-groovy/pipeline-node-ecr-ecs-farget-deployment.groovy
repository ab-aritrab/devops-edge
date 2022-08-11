pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID="420107371191"
        AWS_DEFAULT_REGION="ap-south-1" 
        CLUSTER_NAME="bintech-uat-ecs-cluster"
        SERVICE_NAME="bintech-uat-ui"
        TASK_DEFINITION_NAME="bintech-uat-ui"
        DESIRED_COUNT="1"
        AWS_ECR_LINK="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
        IMAGE_REPO_NAME="bintech_uat_ui_poc"
        IMAGE_TAG="v${BUILD_ID}"
        REPOSITORY_URI = "${AWS_ECR_LINK}/${IMAGE_REPO_NAME}"
        GIT_URL = "https://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@github.com/ab-aritrab/auth-ui-uat.git"
    }

    stages {
        stage('Git') {
            steps {
                /* git credentialsId: 'GOWTHAM_BITBUCKET', poll: false, url: env.GIT_URL */
                git branch: 'main', poll: false, url: env.GIT_URL
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'node:16.16'
                    args '-u root:root'
                    reuseNode true
                }
            }
            steps {
                sh 'chmod -R 777 /root .'
                sh 'npm install'
                sh "CI='' npm run build"
            }
        }
        stage('Build react image'){
            steps{
                sh """
                # use the below command to build the image for Amazon ECS compatibile image
                docker buildx build --platform=linux/amd64 -t ${REPOSITORY_URI}:${IMAGE_TAG} .
                docker tag ${REPOSITORY_URI}:${IMAGE_TAG} ${REPOSITORY_URI}:latest
                """
            }
        }
        stage('Push to ECR'){
            steps{
                sh """
                aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ${AWS_ECR_LINK}
                docker push ${REPOSITORY_URI}:${IMAGE_TAG}
                docker push ${REPOSITORY_URI}:latest
                """
            }
        }
        stage('Deploy') {
            steps{
                script {
                    sh 'chmod +x ./script.sh'
                    sh './script.sh'
                }
            }
        }
        stage('Delete ag-react-example image'){
            steps{
                sh """
                docker rmi -f  ${REPOSITORY_URI}:${IMAGE_TAG}
                docker rmi -f ${REPOSITORY_URI}:latest
                """
            }
        }
    
        stage('Clear workspace'){
            steps{
                cleanWs()
            }
        }
        
    }
}