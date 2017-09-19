package com.rafaltrzcinski.dribshots.shots.details

import com.rafaltrzcinski.dribshots.rest.model.Images
import com.rafaltrzcinski.dribshots.rest.model.Shot
import com.rafaltrzcinski.dribshots.rest.model.Team
import com.rafaltrzcinski.dribshots.rest.model.User
import spock.lang.Specification
import spock.lang.Unroll

class ShotDetailsViewModelSpec extends Specification {

    ShotDetailsViewModel viewModel

    def images = new Images("hidpi", "normal", "teaser")
    def shot = new Shot(1, "shot title", "shot description", images,
            new User(2, "user_name", "user_avatar_url"),
            new Team(3, "team_name", "team_avatar_url"),
            150, 160, 170
    )

    def "setup"() {
        viewModel = new ShotDetailsViewModel(shot)
    }

    def "should get normal image as shot image"() {
        expect:
        viewModel.image == "normal"
    }

    def "should get proper image title"() {
        expect:
        viewModel.title == "shot title"
    }

    @Unroll
    def "should get proper description"() {
        given:
        def newShot = new Shot(1, "shot title", shotDescription, images,
                new User(2, "user_name", "user_avatar_url"),
                new Team(3, "team_name", "team_avatar_url"),
                150, 160, 170
        )
        viewModel = new ShotDetailsViewModel(newShot)

        expect:
        viewModel.description == expectedDescription

        where:
        shotDescription | expectedDescription
        "description"   | "description"
        ""              | ""
        null            | ""
    }

    def "should get proper user avatar url"() {
        expect:
        viewModel.userAvatarUrl == "user_avatar_url"
    }

    def "should get proper user name"() {
        expect:
        viewModel.userName == "user_name"
    }

    @Unroll
    def "should get proper team name or No team instead"() {
        given:
        def newTeam = new Team(3, teamName, "team_avatar_url")

        and:
        def newShot = new Shot(1, "shot_title", "shot_description", images,
                new User(2, "user_name", "user_avatar_url"),
                newTeam,
                150, 160, 170
        )

        and:
        viewModel = new ShotDetailsViewModel(newShot)

        expect:
        viewModel.teamName == expectedTeamName

        where:
        teamName | expectedTeamName
        "Team 1" | "Team 1"
        ""       | ""
        null     | "No team"
    }

    def "should return No team name when team is null"() {
        given:
        def newShot = new Shot(1, "shot_title", "shot_description", images,
                new User(2, "user_name", "user_avatar_url"),
                null,
                150, 160, 170
        )

        and:
        viewModel = new ShotDetailsViewModel(newShot)

        expect:
        viewModel.teamName == "No team"
    }

    @Unroll
    def "should return proper team avatar url"() {
        given:
        def newTeam = new Team(3, "team_name", teamAvatar)

        and:
        def newShot = new Shot(1, "shot_title", "shot_description", images,
                new User(2, "user_name", "user_avatar_url"),
                newTeam,
                150, 160, 170
        )

        and:
        viewModel = new ShotDetailsViewModel(newShot)

        expect:
        viewModel.teamAvatarUrl == expectedTeamAvatar

        where:
        teamAvatar | expectedTeamAvatar
        "avatar"   | "avatar"
        ""         | ""
        null       | ""
    }

    def "should return counters as strings"() {
        expect:
        viewModel.views == "150"
        viewModel.likes == "160"
        viewModel.comments == "170"
    }
}