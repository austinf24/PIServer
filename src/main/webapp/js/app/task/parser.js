define(["require","app/task/script","../trial/trial_sets","../stimulus/stimulus_sets","../media/media_sets","../sequencer/sourceSequence","utils/mixer","../sequencer/sequencePreload"],function(e){var t=e("app/task/script"),n=e("../trial/trial_sets"),r=e("../stimulus/stimulus_sets"),i=e("../media/media_sets"),s=e("../sequencer/sourceSequence"),o=e("utils/mixer"),u=e("../sequencer/sequencePreload");return function(){return t.trialSets&&n(t.trialSets),t.stimulusSets&&r(t.stimulusSets),t.mediaSets&&i(t.mediaSets),s.add(o(t.sequence)),u(t)}});