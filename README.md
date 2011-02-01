A simple demonstration program illustrating the use of Spring's @Transactional
annotation when multiple data sources are present.

# Problem #
Spring 2.5.x provides nice support for declarative transactions via injection
of the `EntityManagerFactory` using `@PersistenceContext` and demarcation of
transactional methods using `@Transactional`. However, these only work if there
is one `EntityManagerFactory` declared in your context. Spring does not honor
`@PersistenceContext(unitName="..."), nor can you specify a transaction manager
 in `@Transactional`.

See also [SPR-3955](https://jira.springframework.org/browse/SPR-3955).

Without support for these annotations, you need to explicitly inject the
`EntityManager[Factory]` and programmatically set transaction boundaries.

# Solution #

This sample app demonstrates a way to maintain at least part of the nice
declarative style while adding more than one data source. You still need to
explicitly inject an `EntityManagerFactory` (and expose it via an interface),
but through the use of an AOP proxy we can intercept calls to @Transactional
methods and wrap them in transactions using the appropriate `EntityManager`.

# Run it #

Simply run "mvn test" to see a passing unit test demonstrating persistence to
two different data sources. The "two-data-sources-works" tag shows this final
desired functionality.

To see the failing unit test introduced when a second data source is added and
Spring ceases to use the `@Transactional` annotations, check out
"two-data-sources-broken".

The "one-data-source" tag represents the baseline functionality Spring provides
when using a single data source and `@PersistenceContext` and `@Transactional`
are fully functional.
