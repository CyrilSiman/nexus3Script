import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.Query;

final REPOSITORY_NAME = 'REPOSITORY_NAME';
final VERSION = ['V1','V2','V3'];
final GROUP = 'com.organization'
final NAMES = ['package1','package2','package3'] 

// Get a repository
def repo = repository.repositoryManager.get(REPOSITORY_NAME);
// Get a database transaction
def tx = repo.facet(StorageFacet).txSupplier().get();
try {
    // Begin the transaction
    tx.begin();

    NAMES.each { name -> 
        def componentVersions = tx.findComponents(Query.builder()
                                                    .where('group = ').param(GROUP)
                                                    .and('name = ').param(name).build(), [repo]);

            //For each version
	        componentVersions.each { component ->
                if (VERSION.contains(component.version())) {
                    log.info("Deleting Component ${component.group()} ${component.name()} ${component.version()}")
                    tx.deleteComponent(component);
                }
            }
    }

} finally {
    // End the transaction
    tx.commit();
    tx.close();
}
