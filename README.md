Jacoco + Sonarqube
steps
1  Add Jacoco plugin
2 mvn clean verify or clean install to generate jacoco report 
  maven stages - compile,Test,Package,Verify,Install - verify phase will genarate report
3  Install SonarQube check which java version is compatible with sonar version
4   go to bin/start sonar.bat file to run sonarQube
5   mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=YOUR_PASSWORD - to sync spring boot project and SonarQube
6   localhost:9000 to view sonar dashboard

swagger
Using Open API defination
1 add dependecy
2 try to access http://localhost:8081/swagger-ui/index.html
                http://localhost:8081/swagger-ui.html
                http://localhost:8081/v3/api-docs
                http://localhost:8081/v3/api-docs.yaml
if spring security + filter class is added then need to permit all the "/swagger-ui/**", "/swagger-ui.html","/v3/api-docs/**","/v3/api-docs.yaml" in both  
