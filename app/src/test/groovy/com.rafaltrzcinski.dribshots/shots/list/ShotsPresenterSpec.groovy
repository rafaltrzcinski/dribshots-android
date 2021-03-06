package com.rafaltrzcinski.dribshots.shots.list

import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.rest.model.Images
import com.rafaltrzcinski.dribshots.rest.model.Shot
import com.rafaltrzcinski.dribshots.rest.model.Team
import com.rafaltrzcinski.dribshots.rest.model.User
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import okhttp3.Headers
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result
import spock.lang.Specification
import spock.lang.Unroll

class ShotsPresenterSpec extends Specification {

    ShotsActivityContract.Presenter presenter

    def view = Mock(ShotsActivityContract.View)
    def apiRequests = Mock(ApiRequests)
    def subscribeOn = new TestScheduler()
    def observeOn = new TestScheduler()

    def images = new Images("hidpi", "normal", "teaser")
    def shot1 = new Shot(1, "title1", "description 1", images, new User(), new Team(), 0, 0, 0)
    def shot2 = new Shot(2, "title2", "description 2", images, new User(), new Team(), 0, 0, 0)


    def "setup"() {
        presenter = new ShotsPresenter(apiRequests, subscribeOn, observeOn)
    }

    def "should keep reference to view on bind"() {
        given:
        assert !presenter.view

        when:
        presenter.bind(view)

        then:
        presenter.view == view
    }

    def "should remove view reference on unbind"() {
        given:
        presenter.view = view

        when:
        presenter.unbind()

        then:
        !presenter.view
    }

    def "should load shots on view when call is succeed"() {
        given:
        presenter.view = view

        and:
        def response = Response.success([shot1, shot2])
        def result = Result.response(response)

        and:
        apiRequests.getShots() >> Flowable.just(result)

        when:
        presenter.getShots()

        and:
        subscribeOn.triggerActions()
        observeOn.triggerActions()

        then:
        1 * view.loadShots([shot1, shot2])
    }

    @Unroll
    def "should set proper next link from header"() {
        given:
        presenter.view = view

        and:
        def headers = new Headers().newBuilder().add("link", link).build()
        def response = Response.success([shot1, shot2], headers)
        def result = Result.response(response)

        and:
        apiRequests.getShots() >> Flowable.just(result)

        when:
        presenter.getShots()

        and:
        subscribeOn.triggerActions()
        observeOn.triggerActions()

        then:
        presenter.currentNext == expectedLink

        where:
        link                                                             | expectedLink
        ""                                                               | ""
        "some other type of link"                                        | ""
        "<some_next_link>; rel=\"next\", <some_prev_link>; rel=\"prev\"" | "some_next_link"
    }

    def "should not load next shots when next link is empty"() {
        given:
        presenter.view = view

        when:
        presenter.getNextShots()

        then:
        0 * apiRequests.getShots(_)
    }

    def "should start loading on get next shots when next link is not empty"() {
        given:
        presenter.view = view
        def nextLink = "some_next_link"
        presenter.currentNext = nextLink

        and:
        apiRequests.getShots(nextLink) >> Flowable.error(new Exception())

        when:
        presenter.getNextShots()

        and:
        subscribeOn.triggerActions()
        observeOn.triggerActions()

        then:
        1 * view.startLoading()
    }

    def "should show connection error dialog on error call and finish loading"() {
        given:
        presenter.view = view

        and:
        apiRequests.getShots() >> Flowable.error(new Exception())

        when:
        presenter.getShots()

        and:
        subscribeOn.triggerActions()
        observeOn.triggerActions()

        then:
        1 * view.showLoadingError()
        1 * view.finishLoading()
    }

    def "should attach shot fragment details view"() {
        given:
        presenter.view = view

        when:
        presenter.openShotDetails(shot1)

        then:
        1 * view.attachShotDetails(shot1)
    }

    def "should composite subscription be empty on start"() {
        expect:
        presenter.compositeDisposable.size() == 0
    }

    def "should add subscription to composite disposable after get shots"() {
        given:
        assert presenter.compositeDisposable.size() == 0

        and:
        apiRequests.getShots() >> Flowable.just([shot1, shot2])

        when:
        presenter.getShots()

        and:
        subscribeOn.triggerActions()
        observeOn.triggerActions()

        then:
        presenter.compositeDisposable.size() == 1
    }

    def "should detach shot details fragment"() {
        given:
        presenter.view = view

        when:
        presenter.hideShotDetails()

        then:
        1 * view.detachShotDetails()
    }
}