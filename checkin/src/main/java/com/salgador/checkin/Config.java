/**
 * Configuration class for db access.
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {HibernateJPAAutoConfiguration.class})
@ComponentScans(value = {@ComponentScan("boot.entry"),
    @ComponentScan("Model"),
        @ComponentScan("Controller"),
        @ComponentScan("DAO"),
        @ComponentScan("Miscallaneous"),
        @ComponentScan("Service")
    })
public class Config
{
    @Value("${db.driver}")
  private String DB_DRIVER;
    @Value("${db.password}")
  private String DB_PASSWORD;
    @Value("${db.url}")
  private String DB_URL;
    @Value("${db.username}")
  private String DB_USERNAME;
    @Value("${hibernate.dialect}")
  private String HIBERNATE_DIALECT;
    @Value("${hibernate.show_sql}")
  private String HIBERNATE_SHOW_SQL;
    @Value("${hibernate.hbm2ddl.auto}")
  private String HIBERNATE_HBM2DDL_AUTO;
    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    /**
     * Session object for Hibernate with class hibernate values.
     * @return A LocalSessionFactoryBean object to manage database access.
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory()
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties.hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect",HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql",HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    /**
     * Gets database access information.
     * @return A DriverManagerDataSource object with this object's database access information.
     */
    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setURL(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager()
    {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    
}


