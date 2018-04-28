#IntelliJ IDEA

Here, we're going to set the code templates to IntelliJ IDEA, so that all members have the same configuration.

To modify the configuration, go to Preferences/Editor/File and Code Templates

##Class template

In the Files tab, modifiy the "Class" template so that it has:
```java
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

#parse("classicClassTemplate.java")
public class ${NAME} {
    
}
```

##Interface template

In the Files tab, modifiy the "Interface" template so that it has:
```java
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

#parse("classicClassTemplate.java")
public interface ${NAME} {
    
}
```

##Singleton template

In the Files tab, modifiy the "Singlteon" template so that it has:
```java
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

#parse("classicClassTemplate.java")
public class ${NAME} {
    

    private ${NAME}() {}
    
    private static class Instance {
        static final ${NAME} instance = new ${NAME}();
    }

    public static ${NAME} getInstance() {
        return Instance.instance;
    }
}
```

##Comment template

In the Includes tab, create a new template, name it "classicClassTemplate" with extension java and paste the following code:
```
/**
 * @authors Daniel Gonzalez Lopez
 * @version 1.0
 */
 ```

##Code Style

Then, go to Preferences/Editor/Code Style, click on the setting button on Scheme line and choose "import Scheme", then "IntelliJ IDEA code style XML" and select "code_style.xml", which is on the same folder as this document.


Now, you're all set !