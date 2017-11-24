import org.sonatype.nexus.repository.storage.StorageFacet
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.storage.Component
import groovy.json.JsonOutput

def completResult = []

repository.repositoryManager.browse().each { Repository repo ->
    StorageFacet storageFacet = repo.facet(StorageFacet)
    def tx = storageFacet.txSupplier().get()

    def results = [:].withDefault { 0 }

    try {

        tx.begin()

        tx.browseComponents(tx.findBucket(repo)).each { component ->
            def size = 0
            if (component.group() != null) {
                
                tx.browseAssets(component).each { asset ->
                    size = size + asset.size()
                }
                results.put(component.group(),(results.get(component.group()) + size))
            }
        }

    } finally {
        tx.close();
    }
    //Convert Map in List of Object
    def resultsValue = results.collect {key, value -> [repo.getName(), key, value]}
    completResult = completResult + resultsValue
}

def sorted = completResult.sort { a, b -> b.value[2] <=> a.value[2] }
def formated = [] 
sorted.each { item -> 
    formated.push("${item[0]} -- ${item[1]} -- ${(item[2]/1024/1024).setScale(2, BigDecimal.ROUND_HALF_UP)}Mo")
}
log.info(JsonOutput.prettyPrint(JsonOutput.toJson(formated)))
