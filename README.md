A simple demonstration program illustrating the use of Spring's @Transactional
annotation when multiple data sources are present.

Fair warning: this is mostly just exploratory code and still has a couple of
shortcomings to solve before being more than a toy. Read on for details.

# Problem #
Spring 2.5.x provides nice support for declarative transactions via injection
of the `EntityManagerFactory` using `@PersistenceContext` and demarcation of
transactional methods using `@Transactional`. However, these only work if there
is one `EntityManagerFactory` declared in your context. Spring does not honor
`@PersistenceContext(unitName="...")`, nor can you specify a transaction manager
 in `@Transactional`.

See also [SPR-3955](https://jira.springframework.org/browse/SPR-3955).

Without support for these annotations, you need to explicitly inject the
`EntityManager[Factory]` and either programmatically set transaction boundaries
or specify them in your XML config with <tx:advice/>.

# Solution #

This sample app demonstrates a way to maintain at least part of the nice
declarative style of using annotations while adding more than one data source.
You still need to explicitly inject an `EntityManagerFactory` (and expose it via
an interface), but through the use of an AOP proxy we can intercept calls to
`@Transactional` methods and wrap them in transactions using the appropriate
`EntityManager`.

# Caveats #

When unit testing your JPA DAOs, you will need to set up each DAO into its
own test application context file. This is probably desirable (though a bit
verbose, XML-wise) because you really should only be testing one piece of
functionality at a time. With only one data source/entity manager in the
test context and a default transaction manager bean, it seems as though we can
rely on Spring to manage the transactional semantics of each unit test
(automatic rollback for each test) while our custom annotation handling manages
our transactional DAOs.

# Run it #

To run the test app demonstrating multiple data sources in the same context,
run `mvn compile && mvn exec:java -Dexec.mainClass="example.App"`.

To run the unit tests for both data sources, run `mvn test`.

To see the failing unit test introduced when a second data source is added and
Spring ceases to use the `@Transactional` annotations, check out the
"two-data-sources-broken" tag. This is a snapshot of the project before we add
our custom transaction handling.

The "one-data-source" tag represents the baseline functionality Spring provides
when using a single data source and `@PersistenceContext` and `@Transactional`
are fully functional.
