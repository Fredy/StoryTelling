<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head lang="en" th:include="fragments/header :: header-conf (pageCss= '/css/index.css')">
  <title id="pageTitle">Home</title>
</head>
<body>
<div th:replace="fragments/header :: header"></div>

<main role="main" class="container">

  <div class="row">
    <!-- Search -->
    <div class="col-md-6 mx-auto">

      <div th:if="${messageWarn != null}" th:text="${messageWarn}"
           class="my-1 text-center text-danger">
      </div>

      <div class="container my-3">
        <form th:method="get" th:action="@{/}">
          <div class="input-group">
            <input th:name="text" type="text" class="form-control"
                   placeholder="Search story proposition...">
            <span class="input-group-btn">
                  <button class="btn btn-secondary" type="submit">Search</button>
                </span>
          </div>
        </form>
      </div>

    </div>

    <!-- New story -->
    <div class="col-10 mb-3 mx-auto">

      <div th:if="${messageNew != null}" th:text="${messageNew}"
           class="my-1 text-center text-danger">
      </div>
      <div th:if="${messageOk != null}" th:text="${messageOk}"
           class="my-1 text-center text-success">
      </div>

      <div id="accordion" role="tablist" aria-multiselectable="true">
        <div class="card">
          <div class="card-header" role="tab" id="headingThree">
            <h5 class="mb-0">
              <a class="collapsed" data-toggle="collapse" data-parent="#accordion"
                 href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                New Story Proposition
              </a>
            </h5>
          </div>
          <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="card-block">
              <div class="well m-4">
                <form th:method="post" th:action="@{/}" role="form" th:object="${newProp}">
                  <div class="form-group">
            <textarea th:name="text" class="form-control my-1" rows="3" th:maxlength="300"
                      placeholder="Text" th:field="*{text}"></textarea>
                    <input th:name="userId" type="number" class="form-control my-1"
                           placeholder="User Id"
                           th:field="*{userId}">
                  </div>
                  <button type="submit" class="btn btn-primary"><i class="fa fa-reply"></i> Submit
                  </button>
                </form>
              </div>

            </div>
          </div>
        </div>
      </div>

    </div>

    <!-- Stories -->
    <div class="col-10 mx-auto" th:each="story : ${stories}">
      <div class="card mb-4">
        <div class="card-body">
          <p class="card-title" th:text="${story.propText}"></p>
          <a th:href="@{'/story/' + ${story.id}}" class="btn btn-primary">Read More &rarr;</a>
        </div>
        <div class="card-footer text-muted">
          <span
              th:text="|Posted on ${#dates.format(story.pubDate, 'MMMMMM dd, yyyy; hh:mm ')} by|"></span>
          <a th:href="@{'/user/' + ${story.user.id}}"
             th:text="|${story.user.firstName} ${story.user.lastName}|">User</a>
        </div>
      </div>
    </div>

    <div class="col-md-8 mx-auto">
      <div th:if="${#lists.isEmpty(stories)}" class="my-1 text-center">
        <h3>No story was found</h3>
      </div>
    </div>
  </div>


</main><!-- /.container -->

</body>
</html>