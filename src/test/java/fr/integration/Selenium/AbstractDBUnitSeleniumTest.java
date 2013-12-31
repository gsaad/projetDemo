package fr.integration.Selenium;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class AbstractDBUnitSeleniumTest extends
		AbstractActionSeleniumTest {

	protected IDatabaseConnection dbconn;
	protected IDataSet dataset;
	@Autowired
	DriverManagerDataSource dataSource;

	@Value("${dbunit.test.datafile}")
	protected String DBUNIT_DATA_FILE;

	@Before
	public void loadDbUnitData() throws Exception {
		Connection conn = dataSource.getConnection();
		dbconn = new DatabaseConnection(conn);
		dataset = dbconn.createDataSet();
		Resource resource = new ClassPathResource(DBUNIT_DATA_FILE);
		if (resource.exists()) {
			File dataSetXmlFile = resource.getFile();
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder()
					.setColumnSensing(true);
			dataset = builder.build(dataSetXmlFile);
			DatabaseOperation.CLEAN_INSERT.execute(dbconn, dataset);
		} else {
			throw new FileNotFoundException("fichier n'existe pas");
		}
	}
}
