# Example Project

The *Zero-Effort ticket management Unimol System* (ZE&trade;US, pronounced "zeus") allows to easily manage
tickets and issues in GitHub repositories.

The main features are:
- Analytics on past (closed) and open issues such as time after first response,
average of days before closing commit and more
- Management of open tickets
- Recommendation about ticket assignees and scheduling
- Files and *LoC* involved in commits
- Fixed, closed and open issues without comments
- Time to last commit
- Numbers of open issues
- Ticket closing time distribution

The authentication is granted with GitHub login API integration. Specific users or repositories (or both) can be analyzed
 with GET request to ZE&trade;US endpoints.
 
 APIs are tested with JUnit and documented in Wiki pages enriched with class diagrams.
