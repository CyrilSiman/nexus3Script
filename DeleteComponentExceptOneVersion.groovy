import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.Query;

final REPOSITORY_NAME = 'your repo';
final VERSION = 'unique version keep';
final GROUP = 'com.organization'
final NAMES = ['package1','package2','package3'] 

// Get a repository
def repo = repository.repositoryManager.get(repositoryName);
// Get a database transaction
def tx = repo.facet(StorageFacet).txSupplier().get();
try {
    // Begin the transaction
    tx.begin();

    NAMES.each { name -> 
        def componentVersions = tx.findComponents(Query.builder()
                                                    .where('group = ').param(GROUP)
                                                    .and('name = ').param(name).build(), [repo]);

        componentVersions.each { component ->
                if (!component.version().startsWith(VERSION)) {
                    log.info("Deleting Component ${component.group()} ${component.name()} ${component.version()}")
                    //Uncomment this line to really delete components
                    //tx.deleteComponent(component);
                }
            }
    }

} finally {
    // End the transaction
    tx.commit();
    tx.close();
}
