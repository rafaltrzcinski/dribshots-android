package com.rafaltrzcinski.dribshots.shots.details

import com.rafaltrzcinski.dribshots.rest.model.Images
import com.rafaltrzcinski.dribshots.rest.model.Shot
import spock.lang.Specification
import spock.lang.Unroll

class ShotDetailsViewModelSpec extends Specification {

    ShotDetailsViewModel viewModel

    def images = new Images("hidpi", "normal", "teaser")
    def shot = new Shot(1, "shot title", "shot description", images)

    def "setup"() {
        viewModel = new ShotDetailsViewModel(shot)
    }

    def "should get normal image as shot image"() {
        expect:
        viewModel.image == shot.images.normal
    }

    def "should get proper image title"() {
        expect:
        viewModel.title == shot.title
    }

    @Unroll
    def "should get proper description"() {
        given:
        def newShot = new Shot(1, "title", shotDescription, images)
        viewModel = new ShotDetailsViewModel(newShot)

        expect:
        viewModel.description == expectedDescription

        where:
        shotDescription | expectedDescription
        "description"   | "description"
        ""              | ""
        null            | ""

    }

}