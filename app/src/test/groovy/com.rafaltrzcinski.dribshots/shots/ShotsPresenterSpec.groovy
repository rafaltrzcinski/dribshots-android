package com.rafaltrzcinski.dribshots.shots

import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.rest.model.Images
import com.rafaltrzcinski.dribshots.rest.model.Shot
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import spock.lang.Specification

class ShotsPresenterSpec extends Specification {

    ShotsActivityContract.Presenter presenter

    def view = Mock(ShotsActivityContract.View)
    def apiRequests = Mock(ApiRequests)
    def subscribeOn = new TestScheduler()
    def observeOn = new TestScheduler()


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
        def images = new Images("hidpi", "normal", "teaser")
        def shot1 = new Shot(1, "title1", "description 1", images)
        def shot2 = new Shot(2, "title2", "description 2", images)

        and:
        apiRequests.getShots() >> Flowable.just([shot1, shot2])

        when:
        presenter.getShots()

        and:
        subscribeOn.triggerActions()
        observeOn.triggerActions()

        then:
        1 * view.loadShots([shot1, shot2])
    }

    def "should show connection error dialog on error call"() {
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
    }
}