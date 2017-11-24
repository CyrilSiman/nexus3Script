Some util Groovy scripts which can be used as task in Nexus 3. 

Create a new execute script task and use one of these scripts



## Information

#### ComputeComponentSize.groovy

Compute size for each component in all repositories (Mo)



## Delete

By default these script only print components informations that can be deleted.

In script : uncomment this line to really delete components

```groovy
//tx.deleteComponent(component);
```

#### DeleteComponentsFilterWithGroupAndName.groovy

Filter component with these parameters

```groovy
final REPOSITORY_NAME = 'your repo'
final GROUP = 'com.organization'
final NAMES = ['package1','package2']
```

#### DeleteComponentExceptOneVersion.groovy

This script delete all component except them which version begin with VERSION constant

```groovy
final REPOSITORY_NAME = 'your repo';
final VERSION = 'unique version keep';
final GROUP = 'com.organization'
final NAMES = ['package1','package2','package3']
```

 

#### DeleteComponentForEachVersion.groovy

Delete only components matching these filters 

```groovy
final REPOSITORY_NAME = 'REPOSITORY_NAME';
final VERSION = ['V1','V2','V3'];
final GROUP = 'com.organization'
final NAMES = ['package1','package2','package3'] 
```

