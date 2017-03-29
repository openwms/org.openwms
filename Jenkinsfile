#!groovy

node {
   def mvnHome
   stage('\u27A1 Preparation') {
      git 'git@github.com:openwms/org.openwms.git'
      mvnHome = tool 'M3'
   }
   parallel (
     "default-build": {
       stage('\u27A1 Build') {
          configFileProvider(
              [configFile(fileId: 'sonatype-settings', variable: 'MAVEN_SETTINGS')]) {
              sh "'${mvnHome}/bin/mvn' -s $MAVEN_SETTINGS clean deploy -Ddocumentation.dir=${WORKSPACE}/target -Psordocs,sonatype -U"
          }
       }
     },
     "sonar-build": {
       stage('\u27A1 Sonar') {
          sh "'${mvnHome}/bin/mvn' clean org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar -Djacoco.propertyName=jacocoArgLine -Dbuild.number=${BUILD_NUMBER} -Ddocumentation.dir=${WORKSPACE}/target -Pjenkins"
       }
     }
   )
   stage('\u27A1 Results') {
      archive '**/target/*.jar'
   }
}

