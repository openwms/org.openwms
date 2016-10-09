#!groovy

node {
   def mvnHome
   stage('Preparation') {

      git 'git@github.com:spring-labs/org.openwms.services.git'
      mvnHome = tool 'M3'
   }
   stage('Build') {

      sh "'${mvnHome}/bin/mvn' clean deploy -Psordocs,sonatype -U"
   }
   stage('Sonar') {

      sh "'${mvnHome}/bin/mvn' clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Djacoco.propertyName=jacocoArgLine -Dbuild.number=${BUILD_NUMBER} -Dbuild.date=${BUILD_ID}"
   }
   stage('Results') {

      archive 'target/*.jar'
   }
}