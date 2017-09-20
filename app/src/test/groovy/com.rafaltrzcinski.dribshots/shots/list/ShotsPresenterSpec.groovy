package com.rafaltrzcinski.dribshots.shots.list

import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.rest.model.Images
import com.rafaltrzcinski.dribshots.rest.model.Shot
import com.rafaltrzcinski.dribshots.rest.model.Team
import com.rafaltrzcinski.dribshots.rest.model.User
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result
import spock.lang.Specification

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
}