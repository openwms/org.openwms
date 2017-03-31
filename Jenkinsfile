#!groovy

node {
  try {
   def mvnHome
   stage('\u27A1 Preparation') {
      git 'git@github.com:openwms/org.openwms.git'
      mvnHome = tool 'M3'
   }
   stage('\u27A1 Build & Deploy') {
      configFileProvider(
          [configFile(fileId: 'maven-local-settings', variable: 'MAVEN_SETTINGS')]) {
          sh "'${mvnHome}/bin/mvn' -s $MAVEN_SETTINGS clean deploy -Dci.buildNumber=${BUILD_NUMBER} -Ddocumentation.dir=${WORKSPACE}/target -Psordocs,sonatype -U"
      }
   }
   stage('\u27A1 Sonar') {
      sh "'${mvnHome}/bin/mvn' clean org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar -Djacoco.propertyName=jacocoArgLine -Dci.buildNumber=${BUILD_NUMBER} -Ddocumentation.dir=${WORKSPACE}/target -Pjenkins"
   }
   stage('\u27A1 Results') {
      archive '**/target/*.jar'
   }
  } finally {
    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
  }
}

