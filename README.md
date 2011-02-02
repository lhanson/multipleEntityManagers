A simple demonstration program illustrating the use of Spring's @Transactional
annotation when multiple data sources are present.

Fair warning: this is mostly just exploratory code and still has a couple of
shortcomings to solve before being more than a toy. Read on for details.

# Problem #
Spring 2.5.x provides nice support for declarative transactions via injection
of the `EntityManagerFactory` using `@PersistenceContext` and demarcation of
transactional methods using `@Transactional`. However, these only work if there
is one `EntityManagerFactory` declared in your context. Spring does not honor
`@PersistenceContext(unitName="..."), nor can you specify a transaction manager
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
@Transactional methods and wrap them in transactions using the appropriate
`EntityManager`.

# Caveats #

When unit testing your JPA DAOs, you will need to set up each DAO into its
own test application context file. This is probably desirable (though a bit
verbose, XML-wise) because you really should only be testing one piece of
functionality at a time, and with only one data source/entity manager in the
test context, you can use the @Transactional annotation normally without any
workarounds.

# Run it #

Simply run "mvn test" to see a passing unit test demonstrating persistence to
two different data sources. HEAD will show this final desired functionality.

To see the failing unit test introduced when a second data source is added and
Spring ceases to use the `@Transactional` annotations, check out the
"two-data-sources-broken" tag.

The "one-data-source" tag represents the baseline functionality Spring provides
when using a single data source and `@PersistenceContext` and `@Transactional`
are fully functional.
