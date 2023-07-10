plugins {
    id("java")
}
repositories {
    mavenCentral()
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
implementation ("com.google.code.gson:gson:2.10.1")
implementation ("io.github.cdimascio:dotenv-java:3.0.0")
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
