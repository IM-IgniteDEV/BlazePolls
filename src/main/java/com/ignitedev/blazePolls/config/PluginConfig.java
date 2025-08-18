package com.ignitedev.blazePolls.config;

import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Comment;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import com.ignitedev.blazePolls.util.TextUtility;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("FieldMayBeFinal")
@Configuration("config.json")
@Getter
public class PluginConfig extends Config {

  @Comment("Title for the poll list GUI")
  private Component guiListTitle = TextUtility.parseMiniMessage("<gold>Active Polls");

  @Comment("Title for the vote GUI")
  private Component guiVoteTitle = TextUtility.parseMiniMessage("<yellow>Poll");

  @Comment("Title for the creation GUI")
  private Component guiCreateTitle = TextUtility.parseMiniMessage("<light_purple>Create Poll");

  @Comment("Maximum options per poll")
  private int maxOptions = 6;

  @Comment("List GUI size (9, 18, 27, 36, 45, 54)")
  private int listGuiSize = 54;

  @Comment("How often (in seconds) to check for poll expiration")
  private int closeCheckIntervalSeconds = 60;

  @Comment("Usage: /createpoll")
  private Component msgUsageCreatePoll =
      TextUtility.parseMiniMessage("<yellow>Usage: /createpoll <duration> <question...>");

  @Comment("Usage: /poll close <pollId>")
  private Component msgUsagePollClose =
      TextUtility.parseMiniMessage("<yellow>Usage: /poll close <pollId>");

  @Comment("Usage: /poll remove <pollId>")
  private Component msgUsagePollRemove =
      TextUtility.parseMiniMessage("<yellow>Usage: /poll remove <pollId>");

  @Comment("Voting: recorded")
  private Component msgVoteRecorded = TextUtility.parseMiniMessage("<green>Vote recorded.");

  @Comment("Voting: cannot vote")
  private Component msgCannotVote =
      TextUtility.parseMiniMessage("<red>You can't vote on this option.");

  @Comment("Creation: option limit reached")
  private Component msgOptionLimit = TextUtility.parseMiniMessage("<red>Option limit reached.");

  @Comment("Creation: add at least two options")
  private Component msgAddAtLeastTwo = TextUtility.parseMiniMessage("<red>Add at least 2 options.");

  @Comment("Creation: poll created")
  private Component msgPollCreated =
      TextUtility.parseMiniMessage("<green>Poll created and active.");

  @Comment("Creation: cancelled")
  private Component msgCreationCancelled =
      TextUtility.parseMiniMessage("<yellow>Creation cancelled.");

  @Comment("Creation: type new option text")
  private Component msgTypeNewOption =
      TextUtility.parseMiniMessage("<yellow>Type the new option text in chat...");

  @Comment("Creation: type new option text for index, {index} placeholder")
  private Component msgTypeNewOptionAtIndex =
      TextUtility.parseMiniMessage(
          "<yellow>Type a new text for option [<white>{index}<yellow>] in chat...");

  @Comment("Creation: added option, {text} placeholder")
  private Component msgAddedOption =
      TextUtility.parseMiniMessage("<green>Added option: <white>{text}");

  @Comment("Creation: updated option at index, {index} and {text} placeholders")
  private Component msgUpdatedOptionAtIndex =
      TextUtility.parseMiniMessage("<green>Updated option [<white>{index}<green>]: <white>{text}");

  @Comment("Poll not found")
  private Component msgPollNotFound = TextUtility.parseMiniMessage("<red>Poll not found.");

  @Comment("Closed poll, {pollId} placeholder")
  private Component msgClosedPoll =
      TextUtility.parseMiniMessage("<green>Closed poll <white>{pollId}");

  @Comment("Removed poll")
  private Component msgRemovedPoll = TextUtility.parseMiniMessage("<green>Removed poll.");

  // GUI: Vote (as Components, MiniMessage)
  @Comment("Vote GUI: title format with {id}")
  private Component guiVoteTitleFormat = TextUtility.parseMiniMessage("<yellow>Poll - {id}");

  @Comment("Vote GUI: question header item name")
  private Component guiVoteQuestionHeaderName = TextUtility.parseMiniMessage("<yellow>Poll");

  @Comment("Vote GUI: question label text")
  private Component guiVoteQuestionLabel = TextUtility.parseMiniMessage("<gray>Question:");

  @Comment("Vote GUI: status text when active")
  private Component guiVoteStatusActive = TextUtility.parseMiniMessage("<green>Active");

  @Comment("Vote GUI: status text when closed")
  private Component guiVoteStatusClosed = TextUtility.parseMiniMessage("<red>Closed");

  @Comment("Vote GUI: option lore lines; supports {votes}")
  private List<Component> guiVoteOptionLore =
      Arrays.asList(
          TextUtility.parseMiniMessage("<gray>Votes: <white>{votes}"),
          Component.text(" "),
          TextUtility.parseMiniMessage("<gray>Click to vote"));

  @Comment("Vote GUI: option item name with {index} and {option}")
  private Component guiVoteOptionName = TextUtility.parseMiniMessage("<aqua>[{index}] {option}");

  // GUI: List
  @Comment("List GUI: item display name with {question}")
  private Component guiListItemName = TextUtility.parseMiniMessage("<yellow>{question}");

  @Comment("List GUI: item lore lines; supports {id} and {created}")
  private List<Component> guiListItemLore =
      Arrays.asList(
          TextUtility.parseMiniMessage("<gray>ID: <white>{id}"),
          TextUtility.parseMiniMessage("<gray>Created: <white>{created}"),
          Component.text(" "),
          TextUtility.parseMiniMessage("<gray>Click to view and vote"));

  @Comment("List GUI: date format for createdAt")
  private String guiListCreatedDateFormat = "yyyy-MM-dd HH:mm";

  // GUI: Create
  @Comment("Create GUI: question header item name")
  private Component guiCreateQuestionItemName = TextUtility.parseMiniMessage("<yellow>Question");

  @Comment("Create GUI: question label for lore (the question text is added dynamically)")
  private Component guiCreateQuestionLabel = TextUtility.parseMiniMessage("<gray>Question:");

  @Comment("Create GUI: duration header item name")
  private Component guiCreateDurationItemName = TextUtility.parseMiniMessage("<yellow>Duration");

  @Comment("Create GUI: duration lore lines; supports {duration}")
  private List<Component> guiCreateDurationLore =
      Collections.singletonList(TextUtility.parseMiniMessage("<gray>Duration: <white>{duration}"));

  @Comment("Create GUI: duration value when none")
  private String guiCreateDurationNone = "none";

  @Comment("Create GUI: add option button name")
  private Component guiCreateAddOptionItemName = TextUtility.parseMiniMessage("<green>Add option");

  @Comment("Create GUI: add option lore lines; supports {max}")
  private List<Component> guiCreateAddOptionLore =
      Arrays.asList(
          TextUtility.parseMiniMessage("<gray>Click and type option text in chat"),
          TextUtility.parseMiniMessage("<gray>Max: <white>{max}"));

  @Comment("Create GUI: remove last button name")
  private Component guiCreateRemoveLastItemName = TextUtility.parseMiniMessage("<red>Remove last");

  @Comment("Create GUI: remove last lore lines")
  private List<Component> guiCreateRemoveLastLore =
      Collections.singletonList(TextUtility.parseMiniMessage("<gray>Removes the last option"));

  @Comment("Create GUI: start button name")
  private Component guiCreateStartItemName = TextUtility.parseMiniMessage("<green>Start");

  @Comment("Create GUI: start lore lines")
  private List<Component> guiCreateStartLore =
      Arrays.asList(
          TextUtility.parseMiniMessage("<gray>Create and activate the poll"),
          TextUtility.parseMiniMessage("<gray>Requires at least 2 options"));

  @Comment("Create GUI: cancel button name")
  private Component guiCreateCancelItemName = TextUtility.parseMiniMessage("<red>Cancel");

  @Comment("Create GUI: cancel lore lines")
  private List<Component> guiCreateCancelLore =
      Collections.singletonList(TextUtility.parseMiniMessage("<gray>Cancel poll creation"));

  @Comment("Create GUI: option edit hint lore lines")
  private List<Component> guiCreateOptionEditHintLore =
      Collections.singletonList(
          TextUtility.parseMiniMessage("<gray>Click to edit (type new text in chat)"));

  @Comment("Create GUI: option item name with {index} and {option}")
  private Component guiCreateOptionName = TextUtility.parseMiniMessage("<aqua>[{index}] {option}");
}
