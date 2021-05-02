pipeline {
    agent any
    tools {
      jdk 'AdoptOpenJDK-14'
    }
    stages {
        stage('build') {
            steps {
                withGradle {
                    sh 'chmod +x ./gradlew'
                    sh './gradlew build'
                    archiveArtifacts artifacts: 'build/libs/Avuna*.jar', followSymlinks: false
                }
            }
        }
    }
}